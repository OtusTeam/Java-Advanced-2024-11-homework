package ru.otus.memory_dump_homework;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.StreamUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.memory_dump_homework.controller.RegistrationController;
import ru.otus.memory_dump_homework.controller.UserController;
import ru.otus.memory_dump_homework.entity.User;
import ru.otus.memory_dump_homework.model.UserDto;
import ru.otus.memory_dump_homework.repository.UserRepository;
import ru.otus.memory_dump_homework.service.RegistrationServiceImpl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.otus.memory_dump_homework.TestUtils.createUser;
import static ru.otus.memory_dump_homework.TestUtils.createUserDto;

class RegistrationControllerTest {

	@Test
	void getById() {
		UserRepository repository = createMockRepo();
		when(repository.findById(1L))
				.thenReturn(Mono.just(createUser()));

		WebTestClient client = createUserClient(repository);
		client.get()
				.uri("/user/1")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isNotEmpty()
				.jsonPath("$.login").isEqualTo("login")
				.jsonPath("$.password").isEqualTo("<PASSWORD>");

	}

	@Test
	void register() {
		UserRepository repository = createMockRepo();
		when(repository.save(any(User.class))).thenReturn(Mono.just(createUser()));

		WebTestClient client = createRegistrationClient(repository);

		client.post()
				.uri("/registration/register")
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(createUserDto()), UserDto.class)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isNotEmpty()
				.jsonPath("$.id").isEqualTo(1);
	}

	@Test
	void getAllUser() throws IOException {
		ClassPathResource resource = new ClassPathResource("find-all.json");
		String json = StreamUtils.copyToString(
				resource.getInputStream(), Charset.defaultCharset()
		);
		UserRepository repository = createMockRepo();
		when(repository.findAll())
				.thenReturn(Flux.fromIterable(List.of(createUser(), new User(1L, "test", "123456"))));

		WebTestClient client = createUserClient(repository);

		client.get()
				.uri("/users")
				.exchange()
				.expectStatus().isOk()
				.expectBody().json(json);

	}

	private WebTestClient createRegistrationClient(UserRepository repository) {
		return WebTestClient.bindToController(
				new RegistrationController(new RegistrationServiceImpl(repository, new ModelMapper()))
		).build();
	}

	private WebTestClient createUserClient(UserRepository repository) {
		return WebTestClient.bindToController(
				new UserController(new RegistrationServiceImpl(repository, new ModelMapper()))
		).build();
	}

	private UserRepository createMockRepo() {
		return Mockito.mock(UserRepository.class);
	}

}
