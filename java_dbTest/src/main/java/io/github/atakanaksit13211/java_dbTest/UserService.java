package io.github.atakanaksit13211.java_dbTest;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface UserService {

    List<Borrowing> getDueBooks(User user);

}
