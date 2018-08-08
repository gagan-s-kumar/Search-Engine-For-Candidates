package com.neu.reviewerfinder.queryengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class AuthorImplTest {
  List<Author> validAuthors;
  List<Author> emptyAuthors;

  @Before
  public void before() throws Exception {
    AuthorDetails a1 = new AuthorDetails("Lutz Michael Wegner", (long) 123);
    AuthorDetails a2 = new AuthorDetails("Michael A. Palley", (long) 124);

    validAuthors = new ArrayList<Author>();
    validAuthors.add(a1);
    validAuthors.add(a2);

    emptyAuthors = null;
  }

  @Test
  public void testEmptyAuthorList() {
    assertNull(emptyAuthors);
  }

  @Test
  public void testValidAuthorListSize() {
    assertEquals(2, validAuthors.size());
  }
}
