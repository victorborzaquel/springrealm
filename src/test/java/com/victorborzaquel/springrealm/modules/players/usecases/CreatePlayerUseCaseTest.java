package com.victorborzaquel.springrealm.modules.players.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.victorborzaquel.springrealm.modules.characters.CharacterEntity;
import com.victorborzaquel.springrealm.modules.characters.CharacterRepository;
import com.victorborzaquel.springrealm.modules.characters.exceptions.CharacterNotFoundException;
import com.victorborzaquel.springrealm.modules.players.PlayerEntity;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.dto.CreatePlayerDto;
import com.victorborzaquel.springrealm.modules.players.dto.ResponsePlayerDto;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
class CreatePlayerUseCaseTest {
  @Mock
  private PlayerRepository playerRepository;
  @Mock
  private CharacterRepository characterRepository;
  @InjectMocks
  private CreatePlayerUseCase createPlayerUseCase;

  private final String username = "victorborzaquel";

  private CreatePlayerDto dto;
  private CharacterEntity character;

  @BeforeEach
  void setUp() {
    dto = CreatePlayerDto.builder()
        .firstName("victor")
        .lastName("borzaquel")
        .characterSlug("guerreiro")
        .username(username)
        .build();

    character = CharacterEntity.builder()
        .slug("guerreiro")
        .build();
  }

  @Test
  void testCreatePlayer() {
    when(characterRepository.findBySlugIgnoreCase(any())).thenReturn(Optional.of(character));

    ResponsePlayerDto response = createPlayerUseCase.execute(dto);

    assertEquals(dto.getFirstName(), response.getFirstName());
    assertEquals(dto.getUsername(), response.getUsername());

    verify(playerRepository, times(1)).save(any());
  }

  @Test
  void testCharacterNotFound() {
    when(characterRepository.findBySlugIgnoreCase(any())).thenReturn(Optional.empty());

    assertThrows(CharacterNotFoundException.class, () -> createPlayerUseCase.execute(dto), "Character not found");

    verify(playerRepository, times(0)).save(any());
  }

  @Test
  void testExistsPlayerUsername() {
    when(playerRepository.existsByUsername(any())).thenReturn(true);

    assertThrows(PlayerAlreadyExistsException.class, () -> createPlayerUseCase.execute(dto), "Player already exists");

    verify(playerRepository, times(0)).save(any());
  }
}