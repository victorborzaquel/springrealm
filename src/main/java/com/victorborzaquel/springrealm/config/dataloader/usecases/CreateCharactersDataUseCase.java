package com.victorborzaquel.springrealm.config.dataloader.usecases;

import org.springframework.stereotype.Component;

import com.victorborzaquel.springrealm.modules.characters.CharacterType;
import com.victorborzaquel.springrealm.modules.characters.dto.CreateCharacterDto;
import com.victorborzaquel.springrealm.modules.characters.usecases.CreateCharacterUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateCharactersDataUseCase {
  private final CreateCharacterUseCase createCharacterUseCase;

  public void execute() {
    // HEROES
    warrior();
    barbarian();
    knight();

    // MONSTERS
    orc();
    giant();
    werewolf();
  }

  private void warrior() {
    CreateCharacterDto dto = CreateCharacterDto.builder()
        .name("Warrior")
        .slug("warrior")
        .life(20)
        .type(CharacterType.HERO)
        .strength(7)
        .defense(5)
        .agility(6)
        .quantityDices(1)
        .quantityFaces(12)
        .build();

    createCharacterUseCase.execute(dto);
  }

  private void barbarian() {
    CreateCharacterDto dto = CreateCharacterDto.builder()
        .name("Barbarian")
        .slug("barbarian")
        .life(21)
        .type(CharacterType.HERO)
        .strength(10)
        .defense(2)
        .agility(5)
        .quantityDices(2)
        .quantityFaces(8)
        .build();

    createCharacterUseCase.execute(dto);
  }

  private void knight() {
    CreateCharacterDto dto = CreateCharacterDto.builder()
        .name("Knight")
        .slug("knight")
        .life(26)
        .type(CharacterType.HERO)
        .strength(6)
        .defense(8)
        .agility(3)
        .quantityDices(2)
        .quantityFaces(6)
        .build();

    createCharacterUseCase.execute(dto);
  }

  private void orc() {
    CreateCharacterDto dto = CreateCharacterDto.builder()
        .name("Orc")
        .slug("orc")
        .life(42)
        .type(CharacterType.MONSTER)
        .strength(7)
        .defense(1)
        .agility(2)
        .quantityDices(3)
        .quantityFaces(4)
        .build();

    createCharacterUseCase.execute(dto);
  }

  private void giant() {
    CreateCharacterDto dto = CreateCharacterDto.builder()
        .name("Giant")
        .slug("giant")
        .life(34)
        .type(CharacterType.MONSTER)
        .strength(10)
        .defense(4)
        .agility(4)
        .quantityDices(2)
        .quantityFaces(6)
        .build();

    createCharacterUseCase.execute(dto);
  }

  private void werewolf() {
    CreateCharacterDto dto = CreateCharacterDto.builder()
        .name("Werewolf")
        .slug("werewolf")
        .life(34)
        .type(CharacterType.MONSTER)
        .strength(7)
        .defense(4)
        .agility(7)
        .quantityDices(2)
        .quantityFaces(4)
        .build();

    createCharacterUseCase.execute(dto);
  }
}
