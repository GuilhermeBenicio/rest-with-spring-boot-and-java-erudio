package br.com.erudio.services;

import br.com.erudio.controllers.BooksController;
import br.com.erudio.data.dto.BooksDTO;
import br.com.erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.model.Books;
import br.com.erudio.repository.BooksRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import static br.com.erudio.mapper.ObjectMapper.parseListObjects;
import static br.com.erudio.mapper.ObjectMapper.parseObject;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BooksServices {

    private final BooksRepository repository;

    private final Logger logger = LoggerFactory.getLogger(BooksServices.class.getName());

    private String registerNotFound = "Não encontramos nenhum registro com esse ID";

    public BooksServices(BooksRepository repository) {
        this.repository = repository;
    }

    public List<BooksDTO> findAllBooks() {

        var books = parseListObjects(repository.findAll(), BooksDTO.class);
        books.forEach(this::addHateoasLinks);
        return books;
    }

    public BooksDTO findById(Long id) {
        var books = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(registerNotFound));
        var dto = parseObject(books, BooksDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BooksDTO create(BooksDTO booksDTO) {
        if (booksDTO == null)
            throw new RequiredObjectIsNullException();

        logger.info("Create the book!");

        var entity = parseObject(booksDTO, Books.class);

        return parseObject(repository.save(entity), BooksDTO.class);
    }

    public BooksDTO update(BooksDTO booksDTO) {

        if (booksDTO == null)
            throw new RequiredObjectIsNullException();

        var books = repository.findById(booksDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(registerNotFound));

        logger.info("Update the book!");

        books.setAuthor(booksDTO.getAuthor());
        books.setLaunchDate(booksDTO.getLaunchDate());
        books.setPrice(booksDTO.getPrice());
        books.setTitle(booksDTO.getTitle());

        var dto = parseObject(repository.save(books), BooksDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one book!");

        Books entity = repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(registerNotFound));

        repository.delete(entity);
    }

    private void addHateoasLinks(BooksDTO dto) {
        dto.add(linkTo(methodOn(BooksController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BooksController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BooksController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BooksController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(BooksController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }

}
