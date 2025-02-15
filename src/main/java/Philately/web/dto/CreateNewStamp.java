package Philately.web.dto;

import Philately.stamp.model.Paper;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class CreateNewStamp {

    @NotNull
    @Size(min = 5,max = 20,message = "Name length must be between 5 and 20 characters!")
    private String name;

    @NotNull
    @Size(min = 5,max = 25,message = "Description length must be between 5 and 25 characters!")
    private String description;

    @NotNull(message = "Please enter a valid image URL!")
    @URL(message = "Please enter a valid image URL!")
    private String imageUrl;

    @NotNull(message = "You must select a type of paper!")
    private Paper paper;

}
