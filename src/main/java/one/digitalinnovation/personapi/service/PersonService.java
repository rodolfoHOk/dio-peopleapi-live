package one.digitalinnovation.personapi.service;

import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exceptions.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

  private PersonRepository personRepository;

  private final PersonMapper personMapper = PersonMapper.INSTANCE;

  @Autowired
  public PersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public MessageResponseDTO createPerson(PersonDTO personDTO) {
    Person personToSave = personMapper.toModel(personDTO);

    Person savedPerson = personRepository.save(personToSave);

    return createMessageResponse(savedPerson.getId(), "Created person with ID ");
  }

  public List<PersonDTO> listAll() {
    List<Person> allPersons = personRepository.findAll();

    return allPersons.stream()
        .map(personMapper::toDTO)
        .collect(Collectors.toList());
  }

  public PersonDTO findById(Long personId) throws PersonNotFoundException {
    Person person = verifyIfExists(personId);

    return personMapper.toDTO(person);
  }

  public void delete(Long personId) throws PersonNotFoundException {
    verifyIfExists(personId);

    personRepository.deleteById(personId);
  }

  public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
    verifyIfExists(id);

    Person personToUpdate= personMapper.toModel(personDTO);

    Person updatedPerson = personRepository.save(personToUpdate);

    return createMessageResponse(updatedPerson.getId(), "Updated person with ID ");
  }

  private Person verifyIfExists(Long personId) throws PersonNotFoundException {
    return personRepository.findById(personId)
        .orElseThrow(() -> new PersonNotFoundException(personId));
  }

  private MessageResponseDTO createMessageResponse(Long personId, String message) {
    return MessageResponseDTO
        .builder()
        .message(message + personId)
        .build();
  }
}
