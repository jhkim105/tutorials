package utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

@Slf4j
public class CookieUtils {

  public static void setCookie(HttpServletResponse response, String name, String value, int maxAgeSeconds, String path) {
    Cookie cookie = new Cookie(name, value);
    cookie.setSecure(false);
    cookie.setMaxAge(maxAgeSeconds);
    cookie.setPath(path);
    response.addCookie(cookie);
  }

  public static String getDirPath(String filePath) {
    if (filePath == null) {
      return null;
    }
    int index = FilenameUtils.indexOfLastSeparator(filePath);
    return filePath.substring(0, index);
  }

  public static Cookie getCookie(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return null;
    }

    Cookie returnCookie = null;
    for (final Cookie thisCookie : cookies) {
      if (thisCookie.getName().equals(name) && !"".equals(thisCookie.getValue())) {
        returnCookie = thisCookie;
        break;
      }
    }

    return returnCookie;
  }


  public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return ;
    }

    for (final Cookie cookie : cookies) {
      if (cookie.getName().equals(name)) {
        cookie.setMaxAge(0);
        response.addCookie(cookie);
      }
    }
  }
}
