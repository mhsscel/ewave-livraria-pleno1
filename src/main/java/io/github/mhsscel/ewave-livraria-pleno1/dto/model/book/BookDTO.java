package io.github.mhsscel.bookjavaapi.dto.model.book;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BookDTO extends RepresentationModel<BookDTO> {

  private Long   id;

  private String title;

  private String genre;

  private String author;

  private Double rating;

  private String url;
}
