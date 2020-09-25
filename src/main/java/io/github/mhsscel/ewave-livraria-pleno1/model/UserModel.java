package io.github.mhsscel.bookjavaapi.model;

import io.github.mhsscel.bookjavaapi.dto.model.user.UserDTO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_USERS")
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String name;

    private String password;

    private String role;

    private Boolean enabled;

    public UserDTO convertEntityToDTO() {
        return new ModelMapper().map(this, UserDTO.class);
    }

    public UserModel convertDTOToEntity(UserDTO userDTO) {
        return new ModelMapper().map(userDTO, UserModel.class);
    }

}
