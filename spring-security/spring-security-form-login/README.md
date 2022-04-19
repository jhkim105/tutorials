Spring Security Form Login
=================================



## Return 401 for REST API call
CustomAuthenticationEntryPoint
```java
public class CustomAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

  public CustomAuthenticationEntryPoint(String loginFormUrl) {
    super(loginFormUrl);
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    if (isRestRequest(request)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    } else {
      super.commence(request, response, authException);
    }
  }

  private boolean isRestRequest(HttpServletRequest req) {
    if (StringUtils.equals(req.getHeader("X-Requested-With"), "XMLHttpRequest")
        || StringUtils.startsWith(req.getHeader("Accept"), "application/json")
        || StringUtils.startsWith(req.getHeader("Accept"), "application/xml"))
      return true;

    return false;
  }
}
```

SecurityConfig
```java
    http
        .authorizeRequests()
            .antMatchers("/", "/home").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .permitAll()
            .and()
        .logout()
            .permitAll()
            .and()
        .exceptionHandling()
          .authenticationEntryPoint(new CustomAuthenticationEntryPoint("/login"));
```


## Test
```java
@SpringBootTest
class UserControllerTest {

  @Autowired
  WebApplicationContext context;

  MockMvc mockMvc;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  @Test
  void me_401() throws Exception {
    mockMvc.perform(get("/users/me")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser("user01")
  void me() throws Exception {
    mockMvc.perform(get("/users/me")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

}
```



## Refs
https://spring.io/guides/gs/securing-web/