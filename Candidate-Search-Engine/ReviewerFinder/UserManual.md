ReviewerFinder v1.0
===================

## User Login.
   This version of the application is only using the user role to gate accessing
   the software.

## Quick Search. 
   This serves as a demo of types of queries we support, which can also be used as shortcuts.

   - `Find authors who published papers in OOPSLA since 2010`

   - `Find authors with at least two published papers in OOPSLA or ECOOP and did not serve on the committe for two consecutive years`

   - `Find authors with similar profiles`
      Here user is asked to input an author's name. The system will look for
      authors similar to given author. We define that one author is similar to 
      other, if one has cited other's paper or vice-versa.

   - `Find authors by keywords in published paper titles`
      If an author has published a paper whose title contains the keywords, the
      author's name will be in the result list.

## Advanced search.
   - Search Criteria: This part of user input is REQUIRED, however user is only
       required to input at least one of the four fields.

      - Journal/Conference name is a dropdown where user can specify which
           journals/conferences the author has to have published paper in.

      - Title keywords: Same as 4th search in Quick Search.

      - Data published since: The author has to have published papers since the
          date picked.

      - Author Like: Same as `Find authors with similar profiles`

   - Filter Criteria: This part is optional. User can filter search result from
       Search Criteria by addiing one or both of the restrictions.

## Author Details.

After each search, user can view the details of an author by select one of the authors in the result list and clicking `Author Details` button in the bottom

