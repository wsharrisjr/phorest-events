package com.phorest.events.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString()
public class ClientData {
  private String id;
  private String email;
  private String firstName;
  private String lastName;
}
