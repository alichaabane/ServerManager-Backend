package com.myApplication.server.model;

import com.myApplication.server.enumeration.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    @NotEmpty(message = "IP Address cannot be empty or null")
    private String ipAddress;
    private String name;
    private Double memory;
    private String type;
    private String imageUrl;
    private Status status;
}
