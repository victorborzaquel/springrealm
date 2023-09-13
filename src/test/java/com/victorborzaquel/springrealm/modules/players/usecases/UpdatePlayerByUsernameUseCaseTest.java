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
import com.victorborzaquel.springrealm.modules.players.PlayerEntity;
import com.victorborzaquel.springrealm.modules.players.PlayerRepository;
import com.victorborzaquel.springrealm.modules.players.dto.ResponsePlayerDto;
import com.victorborzaquel.springrealm.modules.players.dto.UpdatePlayerDto;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerAlreadyExistsException;
import com.victorborzaquel.springrealm.modules.players.exceptions.PlayerNotFoundException;

@ExtendWith(MockitoExtension.class)
class UpdatePlayerByUsernameUseCaseTest {
  @Mock
  private PlayerRepository playerRepository;
  @Mock
  private CharacterRepository characterRepository;
  @InjectMocks
  private UpdatePlayerByUsernameUseCase updatePlayerByUsernameUseCase;

  private final UUID id = UUID.fromString("84f40250-e73f-4ee1-b903-25bcdbb6cddc");
  private final String username = "victorborzaquel";

  private UpdatePlayerDto dto;
  private PlayerEntity entity;
  private CharacterEntity character;

  @BeforeEach
  void setUp() {
    dto = UpdatePlayerDto.builder()
        .firstName("victor")
        .username(username)
        .characterSlug("monster")
        .build();

    character = CharacterEntity.builder().slug("monster").build();

    entity = PlayerEntity.builder()
        .id(id)
        .firstName("victor")
        .username(username)
        .character(character)
        .build();
  }

  @Test
  void testUpdatePlayer() {
    when(playerRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(entity));
    when(characterRepository.findBySlugIgnoreCase(dto.getCharacterSlug())).thenReturn(Optional.of(character));

    ResponsePlayerDto response = updatePlayerByUsernameUseCase.execute(username, dto);

    assertEquals(dto.getFirstName(), response.getFirstName());
    assertEquals(dto.getUsername(), response.getUsername());
    assertEquals(dto.getCharacterSlug(), response.getCharacter().getSlug());

    verify(playerRepository, times(1)).save(any());
  }

  @Test
  void testPlayerNotFound() {
    when(playerRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.empty());

    assertThrows(PlayerNotFoundException.class, () -> updatePlayerByUsernameUseCase.execute(username, dto), "Player not found");

    verify(playerRepository, times(0)).save(any());
  }

  @Test
  void testExistsPlayerUsername() {
    when(playerRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(entity));
    when(playerRepository.existsByUsernameAndIdNot(dto.getUsername(), id)).thenReturn(true);

    assertThrows(PlayerAlreadyExistsException.class, () -> updatePlayerByUsernameUseCase.execute(username, dto));

    verify(playerRepository, times(0)).save(any());
  }
}