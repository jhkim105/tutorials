package jhkim105.tutorials.dto;


import jhkim105.tutorials.service.SimpleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SimpleMapperUsingInjectService {

  protected SimpleService simpleService;

  @Autowired
  public void setSimpleService(SimpleService simpleService) {
    this.simpleService = simpleService;
  }

  @Mapping(target = "name", expression = "java(simpleService.uppercase(source.name()))")
  public abstract SimpleDestination sourceToDestination(SimpleSource source);


}
