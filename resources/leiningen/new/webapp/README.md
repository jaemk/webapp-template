# {{name}}

> A simple item storage system

## Build/Installation

Build from source or see [releases](https://github.com/jaemk/{{name}}/releases)
for pre-built executables (jre is still required)

```
# generate a standalone jar wrapped in an executable script
$ lein bin
```

## Database

[`migratus`](https://github.com/yogthos/migratus) is used for migration management.

```
# create db
$ sudo -u postgres psql -c "create database {{name}}"

# create user
$ sudo -u postgres psql -c "create user {{name}}"

# set password
$ sudo -u postgres psql -c "alter user {{name}} with password '{{name}}'"

# allow user to create schemas in database
$ sudo -u postgres psql -c "grant create on database {{name}} to {{name}}"

# allow user to create new databases
$ sudo -u postgres psql -c "alter role {{name}} createdb"

# apply migrations from repl
$ lein with-profile +dev repl
user=> (cmd/migrate!)
```

## Usage

```
# start the server
$ export PORT=3003        # default
$ export REPL_PORT=3999   # default
$ export INSTRUMENT=false # disable spec assertions
$ bin/{{name}}

# connect to running application
$ lein repl :connect 3999
user=> (initenv)  ; loads a bunch of namespaes
user=> (cmd/add-user "you") 
user=> (cmd/list-users)
```

## Testing

```
# run test
$ lein midje

# or interactively in the repl
$ lein with-profile +dev repl
user=> (autotest)
```

## Docker

```
# build
$ docker build -t {{name}}:latest .
# run
$ docker run --rm -p 3003:3003 -p 3999:3999 --env-file .env.values {{name}}:latest

## License

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
