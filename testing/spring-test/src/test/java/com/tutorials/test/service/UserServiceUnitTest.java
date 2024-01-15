package com.tutorials.test.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tutorials.test.domain.User;
import com.tutorials.test.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Test
  void testGetAllUsers() {
    when(userRepository.findAll()).thenReturn(Arrays.asList(
        User.createForTest("user1", "pass1"),
        User.createForTest("user1", "pass1")
    ));

    List<User> userList = userService.getAll();

    assertThat(userList).isNotNull();
    assertThat(userList.size()).isEqualTo(2);
  }

  @Test
  void testGetById() {
    when(userRepository.findById("1")).thenReturn(Optional.of(User.createForTest("user1", "pass1")));

    User user = userService.getById("1");

    assertThat(user).isNotNull();
    assertThat(user.getUsername()).isEqualTo("user1");
  }

  @Test
  void testGetByIdNotFound() {
    when(userRepository.findById("nonexistent")).thenReturn(Optional.empty());

    assertThrows(DataNotFoundException.class, () -> userService.getById("nonexistent"));
  }

  @Test
  void testSave() {
    User userToSave = User.createForTest("user3", "pass3");
    when(userRepository.save(userToSave)).thenReturn(userToSave);

    User savedUser = userService.save(userToSave);

    assertThat(savedUser).isNotNull();
    assertThat(userToSave.getUsername()).isEqualTo(savedUser.getUsername());
  }

  @Test
  void testDeleteById() {
    userService.deleteById("1");

    verify(userRepository, times(1)).deleteById("1");

    ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
    verify(userRepository).deleteById(argumentCaptor.capture());

    // 전달 인자 검증
    String idToDelete = argumentCaptor.getValue();
    assertThat(idToDelete).isNotNull();
    assertThat(idToDelete).isEqualTo("1");
  }

}