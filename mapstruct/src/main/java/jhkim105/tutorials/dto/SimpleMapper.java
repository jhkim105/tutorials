package jhkim105.tutorials.dto;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SimpleMapper {

  SimpleDestination sourceToDestination(SimpleSource source);

  SimpleSource destinationToSource(SimpleDestination destination);

}
