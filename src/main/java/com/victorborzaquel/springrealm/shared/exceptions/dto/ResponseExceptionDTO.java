package com.victorborzaquel.springrealm.shared.exceptions.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseExceptionDTO {
  private LocalDateTime timestamp;
  private String reason;
  private Integer status;
  private String message;
  private List<String> errors;
}
