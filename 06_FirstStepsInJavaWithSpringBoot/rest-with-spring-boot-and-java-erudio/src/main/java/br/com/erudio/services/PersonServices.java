package br.com.erudio.services;

import br.com.erudio.data.dto.PersonDTO;
import br.com.erudio.exception.ResourceNotFoundException;
import static br.com.erudio.mapper.ObjectMapper.parseListObjects;
import static br.com.erudio.mapper.ObjectMapper.parseObject;

import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());


   private final PersonRepository repository;

    public PersonServices(PersonRepository repository) {
        this.repository = repository;
    }

    public List<PersonDTO> findAll() {
        logger.info("Finding all People!");
        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person!");

        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não encontramos nenhum registro com esse ID"));

        return parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Creating one Person!");
        var entity = parseObject(person, Person.class);

         return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person) {
        logger.info("Updating one Person!");

        Person entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("Não encontramos nenhum registro com esse ID"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public ResponseEntity<String> delete(Long id) {
        logger.info("Deleting one Person!");

        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não encontramos nenhum registro com esse ID"));

        repository.delete(entity);

        return new ResponseEntity<String>("Usuário deletado com sucesso", HttpStatus.OK);
    }
}
