package io.github.mhsscel.bookjavaapi.dto.model.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BookRecentDTO extends RepresentationModel<BookRecentDTO> {

  private Long          id;

  @NotNull(message="title cannot be null")
  private String        title;

  @NotNull(message="genre cannot be null")
  private String        genre;

  @NotNull(message="author cannot be null")
  private String        author;

  @NotNull(message="rating cannot be null")
  private Double rating;

  @NotNull(message="RecentDate cannot be null")
  @JsonSerialize(using = ToStringSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", locale = "en-US", timezone = "Brazil/East")
  private LocalDateTime   date;
}
