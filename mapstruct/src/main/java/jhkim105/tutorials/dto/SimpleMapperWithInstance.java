package jhkim105.tutorials.dto;


import org.mapstruct.factory.Mappers;

public interface SimpleMapperWithInstance {

  SimpleMapperWithInstance INSTANCE = Mappers.getMapper(SimpleMapperWithInstance.class);

  SimpleDestination sourceToDestination(SimpleSource source);

}
