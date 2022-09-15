# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* Quick summary

<code>
	INSERT INTO roles(name) VALUES('ROLE_USER');
	INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
	INSERT INTO roles(name) VALUES('ROLE_ADMIN');
</code>

http://localhost:8080/api/auth/signup POST

<code>

{
	"username": "admin",
	"email": "admin@test.com",
	"password": "test",
	"role": ["admin"]
}

</code>


http://localhost:8080/api/auth/signin POST

<code>

{
	"username": "admin",
	"password": "test"
}

</code>

<code>
http://localhost:8080/api/auth/refreshToken POST

{"refreshToken": "ab0bcb12-f992-4768-9959-8ff2e5d5443e"}


</code>


* Version
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

* Summary of set up
* Configuration
* Dependencies
* Database configuration
* How to run tests
* Deployment instructions

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact