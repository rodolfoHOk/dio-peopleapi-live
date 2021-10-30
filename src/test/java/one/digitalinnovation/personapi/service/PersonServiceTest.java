package one.digitalinnovation.personapi.service;

import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.repository.PersonRepository;
import one.digitalinnovation.personapi.utils.PersonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

  @Mock
  private PersonRepository personRepository;

  @InjectMocks
  private PersonService personService;

  @Test
  void testGivenPersonDTOThanReturnASavedMessage() {
    PersonDTO personDTO = PersonUtils.createFakeDTO();
    Person expectedSavedPerson = PersonUtils.createFakeEntity();

    when(personRepository.save(any(Person.class))).thenReturn(expectedSavedPerson);

    MessageResponseDTO expectedSuccessMessage = createExpectedMessageResponse(expectedSavedPerson.getId());

    MessageResponseDTO successMessage = personService.createPerson(personDTO);

    assertEquals(expectedSuccessMessage, successMessage);
  }

  private MessageResponseDTO createExpectedMessageResponse(Long personId) {
    return MessageResponseDTO
        .builder()
        .message("Created person with ID " + personId)
        .build();
  }
}
