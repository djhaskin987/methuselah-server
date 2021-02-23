Organizations are primarily a way to separate resources and control their
access. Yes, also as a namespace, but primarily as a permissions mechanism, but cheifly an organization mechanism.

We almost NEVER want to do a table scan of all revisions in the whole repo,
only revisions having to do with a particular series. This is ok. It simply
means we are squarely in OLTP territory. DB can handle this.

But it also means that document DB would be a fine fit for this use case.

I mean, it would be easier to secure by URL if the URL were heierarchical, at
least. also easier for the user, who knows names but not IDs.

