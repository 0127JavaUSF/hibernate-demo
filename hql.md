# Hibernate Query Language (HQL)

## What is it?

Hibernate Query Language is a model-centric querying language
based on SQL.  HQL is specific to Hibernate.  In later version
of JPA, they've defined a similar syntax called JPQL. Instead
of referencing tables and columns, in HQL you reference classes
and properties. The syntax also supports some abbreviated query
syntax for convenience.  Additionally, as with Hibernate we tend
to receive the entire model we don't need to define which columns
we intend to retrieve individually - we only need to define the
table.