Just gathering some thoughts I had around API design.

The following is verbose, long, and requires extra validation, BUT has some
serious advantages:

- It's easier to externalize authorization. It's easy for outside sources to
  map things based solely on URL.
- Even if I internalized auth, I'd have to check all of these things anyway to
  implement authorization anyways. (Mostly, basically)
- It doesn't have to be ids, it can be names, and I'd be able to make it work,
  resulting in a much easier to use API.
- names don't work for revisions, but I suppose series numbers do.
- there's nothing for captures, but perhaps this could be mitigated by just
  rolling captures into revisions return (but I don't want to do this).

There are some disadvantages, though:

- Names have to be "slugs" to make any sense; they have to be url-friendly.
  This might just be an advantage though.
- Names' uniqueness within a container must be enforced at the app level. Not a
  big deal
- The URLs are really long. I don't like this.

POST   /api/v1/organizations
GET    /api/v1/organizations
GET    /api/v1/organizations/<number>
PUT    /api/v1/organizations/<number>
DELETE /api/v1/organizations/<number>
HEAD   /api/v1/organizations/<number>

POST   /api/v1/organizations/<id>/repositories
GET    /api/v1/organizations/<id>/repositories
GET    /api/v1/organizations/<id>/repositories/<id>
PUT    /api/v1/organizations/<id>/repositories <-- just metadata
DELETE /api/v1/organizations/<id>/repositories <-- just metadata

POST   /api/v1/organizations/<id>/repositories/<id>/series
GET    /api/v1/organizations/<id>/repositories/<id>/series
GET    /api/v1/organizations/<id>/repositories/<id>/series/<id> <-- just metadata
PUT    /api/v1/organizations/<id>/repositories/<id>/series/<id> <-- just metadata
DELETE /api/v1/organizations/<id>/repositories/<id>/series/<id>

POST   /api/v1/organizations/<id>/repositories/<id>/series/<id>/revisions
GET    /api/v1/organizations/<id>/repositories/<id>/series/<id>/revisions
GET    /api/v1/organizations/<id>/repositories/<id>/series/<id>/revisions/<id> <-- just metadata
PUT    /api/v1/organizations/<id>/repositories/<id>/series/<id>/revisions/<id> <-- just metadata
DELETE /api/v1/organizations/<id>/repositories/<id>/series/<id>/revisions/<id>

POST   /api/v1/organizations/<id>/repositories/<id>/series/<id>/revisions/<id>/captures
GET    /api/v1/organizations/<id>/repositories/<id>/series/<id>/revisions/<id>/captures
GET    /api/v1/organizations/<id>/repositories/<id>/series/<id>/revisions/<id>/captures/<id>
PUT    /api/v1/organizations/<id>/repositories/<id>/series/<id>/revisions/<id>/captures/<id>
DELETE /api/v1/organizations/<id>/repositories/<id>/series/<id>/revisions/<id>/captures/<id>

The problem with the above aproach is that, strictly speaking, there is a lot
of representable garbage. However, there are ways to deal with it: 400, and
404. It just means silly validation steps need to be done, but it is also clean
that way.

An alternative approach.

POST /api/v1/organizations
GET /api/v1/organizations
GET /api/v1/organizations/<number>
PUT /api/v1/organizations/<number>
DELETE /api/v1/organizations/<number>
HEAD /api/v1/organizations/<number>
POST /api/v1/organizations/<id>/repositories
GET /api/v1/organizations/<id>/repositories

GET /api/v1/repositories/<id>
PUT /api/v1/repositories/<id>
DELETE /api/v1/repositories/<id>
POST /api/v1/repositories/<id>/series <-- just metadata
GET /api/v1/repositories/<id>/series <-- just metadata

GET /api/v1/series/<id>
PUT /api/v1/series/<id>
DELETE /api/v1/series/<id>
POST /api/v1/series/<id>/revisions <-- just metadata
GET /api/v1/series/<id>/revisions <-- just metadata

GET /api/v1/revisions/<id> <-- just metadata
POST /api/v1/revisions
DELETE /api/v1/revisions/<id>
GET /api/v1/revisions/<id>/captures

TO authorize the above, I'll have to figure out exactly what organization and
repository I'm in anyway, also which series. So, there's that.

