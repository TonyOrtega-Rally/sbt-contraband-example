# Using sbt-contraband with Rally Versioning Plugin

This demo is forked from [dataday/sbt-contraband-example](https://github.com/dataday/sbt-contraband-example). It has a demonstration of some of the features provided by [sbt-contraband](https://github.com/sbt/contraband). This was mainly aimed at seeing what came from generating 'pseudo case classes in Scala' via [sbt-contraband](https://github.com/sbt/contraband) and in comparison to creating [scala case classes](http://docs.scala-lang.org/tutorials/tour/case-classes.html) from the ground up.

More information on both `sbt` and `sbt-contraband` can be found via the following links.

>[..] a build tool for Scala, Java, and more.
> - http://www.scala-sbt.org

>[..] a description language for your datatypes and APIs, currently targeting Java and Scala.
> - http://www.scala-sbt.org/contraband

The example below will show you how to add new fields to Contraband type records (aka psuedo case classes) and pass the SemVer checks from the [Rally Versioning Plugin](https://github.com/AudaxHealthInc/rally-versioning)
## Start

  ```bash
  > sbt
  > set semVerCheckOnCompile := false
  > set semVerCheckOnPublish := false
  > ;clean ;compile ;publishLocal
  > reload
  ```
This will initially turn off SemVer checks since you won't have anything in your local Ivy repository for SemVer to compare too. Then we will compile and publish the current version, v3.2.0 in your local ivy repo. Last we will reload the sbt console to enable the SemVer plugin again and discard the ```set semVerCheckOnCompile := false``` and ```semVerCheckOnPublish := false```  settings. 

Review the Greetings class generated in the target folder by the Contraband plugin. 

```scala
/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package api.schema
/** new simple test type without objects from initial example code */
final class Greetings private (
  val sub: String,
  val iss: String,
  val rel: String,
  val iat: Option[String],
  val reln: Option[String],
  val impId: Option[String]) extends Serializable {
  
  private def this(sub: String, iss: String, rel: String, iat: Option[String]) = this(sub, iss, rel, iat, Option("csr"), None)
  
  override def equals(o: Any): Boolean = o match {
    case x: Greetings => (this.sub == x.sub) && (this.iss == x.iss) && (this.rel == x.rel) && (this.iat == x.iat) && (this.reln == x.reln) && (this.impId == x.impId)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (37 * (37 * (17 + "api.schema.Greetings".##) + sub.##) + iss.##) + rel.##) + iat.##) + reln.##) + impId.##)
  }
  override def toString: String = {
    "Greetings(" + sub + ", " + iss + ", " + rel + ", " + iat + ", " + reln + ", " + impId + ")"
  }
  private[this] def copy(sub: String = sub, iss: String = iss, rel: String = rel, iat: Option[String] = iat, reln: Option[String] = reln, impId: Option[String] = impId): Greetings = {
    new Greetings(sub, iss, rel, iat, reln, impId)
  }
  def withSub(sub: String): Greetings = {
    copy(sub = sub)
  }
  def withIss(iss: String): Greetings = {
    copy(iss = iss)
  }
  def withRel(rel: String): Greetings = {
    copy(rel = rel)
  }
  def withIat(iat: Option[String]): Greetings = {
    copy(iat = iat)
  }
  def withIat(iat: String): Greetings = {
    copy(iat = Option(iat))
  }
  def withReln(reln: Option[String]): Greetings = {
    copy(reln = reln)
  }
  def withReln(reln: String): Greetings = {
    copy(reln = Option(reln))
  }
  def withImpId(impId: Option[String]): Greetings = {
    copy(impId = impId)
  }
  def withImpId(impId: String): Greetings = {
    copy(impId = Option(impId))
  }
}
object Greetings {
  
  def apply(sub: String, iss: String, rel: String, iat: Option[String]): Greetings = new Greetings(sub, iss, rel, iat)
  def apply(sub: String, iss: String, rel: String, iat: String): Greetings = new Greetings(sub, iss, rel, Option(iat))
  def apply(sub: String, iss: String, rel: String, iat: Option[String], reln: Option[String], impId: Option[String]): Greetings = new Greetings(sub, iss, rel, iat, reln, impId)
  def apply(sub: String, iss: String, rel: String, iat: String, reln: String, impId: String): Greetings = new Greetings(sub, iss, rel, Option(iat), Option(reln), Option(impId))
}

```


To simulate the addition of new fields to this Greetings class, uncomment all the new fields, "a" "b" and "c", in the Contraband type record of Greetings found in the [schema.contra](src/main/contraband/schema.contra) file. Comments in the schema are denoted with ```##```.

```graphql
## new simple test type without objects from initial example code
type Greetings{
 sub: String!
 iss: String!
 rel: String!
 iat: String
 reln: String = "csr" @since("3.2.0")
 impId: String = null @since("3.2.0")
## a: String! = "self" @since("3.3.0") ## New required field requires a default value to avoid major breaking change by SemVer
## b: Int @since("3.3.0")             ## New optional field will default to 'None'
## c: String @since("3.3.0")          ## New optional field will default to 'None'


## ##inject some code for @since("3.3.0") to add new toString method

## #x def customToString = s"original fields:  sub=${sub} iss=${iss} rel=${rel} iat=${iat} reln=${reln}"
## #xcompanion def sayHi = s"Hello from the companion object"
## #xtostring  return s"sub=${sub} iss=${iss} rel=${rel} iat=${iat} reln=${reln} impId=${impId} a=${a} b=${b} c=${c}"
}

```


Now compile to see if the new fields in the Greetings class pass SemVer checks.

  ```bash
  > sbt
  > ;clean ;compile
  ```
  

You will see some output from SemVer showing that the current changes are forward compatible and will require at least a minor version bump. 


```
[success] Total time: 1 s, completed Nov 16, 2018 9:46:55 AM
[info] Updating {file:/Users/otoniel.ortega/test/dir3/sbt-contraband-example/}root...
[info] Resolving jline#jline;2.14.1 ...
[info] Done updating.
[info] Compiling 19 Scala sources to /Users/otoniel.ortega/test/dir3/sbt-contraband-example/target/scala-2.12/classes...
[info] [SemVer] Checking ENABLED with LIMIT target semVerVersionLimit=3.999.999
[info] [SemVer] Check type: comparing most recent release with post-tag changes
[info] [SemVer] Check starting (prev=3.2.0 vs curr=3.999.999) ...
[info] Resolving joda-time#joda-time;2.9.9 ...
[info] [SemVer] Errors total=14, backward=0, forward=14, required diffType=minor
[info] [SemVer] (1/14) Forward -> method a()java.lang.String in class api.schema.Greetings does not have a correspondent in previous version
[info] [SemVer] (2/14) Forward -> method apply(java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,Int,java.lang.String)api.schema.Greetings in object api.schema.Greetings does not have a correspondent in previous version
[info] [SemVer] (3/14) Forward -> method apply(java.lang.String,java.lang.String,java.lang.String,scala.Option,scala.Option,scala.Option,java.lang.String,scala.Option,scala.Option)api.schema.Greetings in object api.schema.Greetings does not have a correspondent in previous version
[info] [SemVer] (4/14) Forward -> method b()scala.Option in class api.schema.Greetings does not have a correspondent in previous version
[info] [SemVer] (5/14) Forward -> method c()scala.Option in class api.schema.Greetings does not have a correspondent in previous version
[info] [SemVer] (6/14) Forward -> method customToString()java.lang.String in class api.schema.Greetings does not have a correspondent in previous version
[info] [SemVer] (7/14) Forward -> method sayHi()java.lang.String in object api.schema.Greetings does not have a correspondent in previous version
[info] [SemVer] (8/14) Forward -> method this(java.lang.String,java.lang.String,java.lang.String,scala.Option,scala.Option,scala.Option,java.lang.String,scala.Option,scala.Option)Unit in class api.schema.Greetings does not have a correspondent in previous version
[info] [SemVer] (9/14) Forward -> method withA(java.lang.String)api.schema.Greetings in class api.schema.Greetings does not have a correspondent in previous version
[info] [SemVer] (10/14) Forward -> method withB(Int)api.schema.Greetings in class api.schema.Greetings does not have a correspondent in previous version
[info] [SemVer] (11/14) Forward -> method withB(scala.Option)api.schema.Greetings in class api.schema.Greetings does not have a correspondent in previous version
[info] [SemVer] (12/14) Forward -> method withC(java.lang.String)api.schema.Greetings in class api.schema.Greetings does not have a correspondent in previous version
[info] [SemVer] (13/14) Forward -> method withC(scala.Option)api.schema.Greetings in class api.schema.Greetings does not have a correspondent in previous version
[info] [SemVer] (14/14) Forward -> object api.schema.BreakingService does not have a correspondent in previous version
[info] [SemVer] version=3.999.999 PASSED: required diffType=minor achieved
[success] Total time: 2 s, completed Nov 16, 2018 9:46:56 AM
```

Notice that SemVer passed, but you will need a minor version upgrade to publish according to ```required diffType=minor```.
This is due to the fact that the new fields 'a', 'b' and 'c' were added when the schema fields were uncommented and generated with Contraband.  


  
Also notice that the ```@since("x.y.z")``` tag in the [schema.contra](src/main/contraband/schema.contra) file has the minor version bumped from the previous release. If you leave the ```@since("x.y.z")``` tag out of your new fields, or you add it but don't increase the minor version, the code generated by the Contraband plugin will trigger a major version change requirement by SemVer. Once all your new fields and commits are in place and you pass the SemVer check, you will be ready to tag your last commit and ```publishLocal``` to simulate a new version. Then you can go ahead and try this whole exercise again to see that SemVer will still work with the addition of new fields.

You can also play around with injecting code into the generated class with the ```#x``` fields by uncommenting them in the schema. More examples of injecting code like this can be found on the [Contraband](https://www.scala-sbt.org/contraband/Combined+Pages.html) docs page

## Simulating the problems with case class

If you look at the [BreakingService](src/main/scala/api/schema/BreakingService.scala) class, you will find a case class called RsClaimSet. If you try adding any field to this case class it will trigger a breaking major change requirement by the SemVer checks. Changing this case class in spite of it not being used anywhere is an example of why you don't want to you case classes in your libraries.

##### SemVer results after adding a new field to case class

```
[error] [SemVer] Errors total=19, backward=4, forward=15, required diffType=major
[error] [SemVer] (1/19) Backward -> method apply(java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String)api.schema.BreakingService#RsClaimSet in object api.schema.BreakingService#RsClaimSet does not have a correspondent in current version
[error] [SemVer] (2/19) Backward -> method copy(java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String)api.schema.BreakingService#RsClaimSet in class api.schema.BreakingService#RsClaimSet does not have a correspondent in current version
[error] [SemVer] (3/19) Backward -> method this(api.schema.BreakingService,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String)Unit in class api.schema.BreakingService#RsClaimSet does not have a correspondent in current version
[error] [SemVer] (4/19) Backward -> the type hierarchy of object api.schema.BreakingService#RsClaimSet is different in current version. Missing types {scala.runtime.AbstractFunction5}
```  
Notice the message ```required diffType=major```. This small update to the case class breaks backward binary compatibility and requires a major version change. When we added the new fields to the Contraband type record ```Greetings```, only forward compatibiity was effected           

## Guidelines to adding new fields in Contrband Type Record

* Adding any new field will require at least a minor version bump by SemVer

* Add new fields to the end of the parameter list to easily see the evolution of the Contraband type record class.

* Always add the ```@since("x.y+1.x")``` annotation to the new fields. And make sure the minor "y" version is higher than the previous version in the type record.


```graphql
 impId: String = null @since("3.2.0") ## Last release change
 a: String! = "self" @since("3.3.0") ## New required field requires a default value to avoid major breaking change by SemVer
```
* To avoid a breaking major change in SemVer, add a default value to required fields denoted by the ```!``` in the schema type record. Optional fields do not require a default value to avoid major breaks since they will default to ```None``` in the default ```private def this(...)``` constructor of the generated class

* When testing the addition of new fields, you will need to make sure you have the latest release version in your local Ivy repostory. In this case its v3.2.0. This will be required for the SemVer plugin to do its checks against your latest commits or local changes. For testing purposes you might need to do reset to the last tag,  
```git reset --hard v3.2.0```, then ```sbt clean compile publishLocal``` to have your v3.2.0 in your local ivy repository.

* While playing with this test code, you might be playing around with git version tags, commits, local changes, and publishing locally. When testing changes and you are in the sbt console, do a ```sbt> reload``` so that the Rally versioning plugin can pick up your latest git version from your git log. Then when you compile the SemVer check will correctly check against the right previous version.

```
> reload
[info] Loading global plugins from /Users/otoniel.ortega/.sbt/0.13/plugins
[info] Loading project definition from /Users/otoniel.ortega/test/dir3/sbt-contraband-example/project
[info] Skipping fetching tags from git remotes; to enable, set the system property version.autoFetch=true
[info] RallyVersioningPlugin set versionFromGit=3.2.0-dirty-SNAPSHOT
[info] RallyVersioningPlugin set version=3.2.0-dirty-SNAPSHOT
[info] RallyVersioningPlugin set isCleanRelease=false
[info] Set current project to api-schema (in build file:/Users/otoniel.ortega/test/dir3/sbt-contraband-example/)
>
```

##### Ivy repository after successfully publishing v3.2.0 locally
```bash
$[~/.ivy2/local/api.schema/api-schema_2.12]>ll
total 0
drwxr-xr-x  5 otoniel.ortega  staff   160B Nov 14 12:36 2.0.0/
drwxr-xr-x  5 otoniel.ortega  staff   160B Nov 14 12:51 2.0.1-1-32ce387-SNAPSHOT/
drwxr-xr-x  5 otoniel.ortega  staff   160B Nov 14 12:53 2.1.0/
drwxr-xr-x  5 otoniel.ortega  staff   160B Nov 15 16:02 3.0.0/
drwxr-xr-x  5 otoniel.ortega  staff   160B Nov 15 16:08 3.1.0/
drwxr-xr-x  5 otoniel.ortega  staff   160B Nov 15 16:23 3.2.0/
```
The SemVer plugin will do its binary compatibility checks based on the latest version of the the latest release tag in your git log. In this case the last tag was v3.2.0

```bash
$[~/code/sbt-contraband-example]> git log --oneline --decorate --graph -3
* b0b3a38 (HEAD -> master, tag: v3.2.0, origin/master) added two new fields to Greetings. "reln" is a String has a default value "impId" is a String that has a default None value
* a6226da (tag: v3.1.0) added new method returning a contraband object Greetings
* b4f9d48 (tag: v3.0.0) updated build lower bound
```


When you compile the project, the classes generated by Contraband will be found in the target directory under /scala-2.12/src_managed/main/api.schema/ . There you will find the generated Greetings class. 

##### Things to notice in this Contraband class
* How the comments are injected into the class as Scala docs. 
* How code can be injected like ```def customToString...```
* How the default ```private def this(...)``` constructors are generated when adding new fields as optional fields or fields with default parameters
* How ```def sayHi``` was injected into the companion object
* How the companion object generates multiple apply methods while keeping the original ones for backwards compatibility.

```scala
/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package api.schema
/**
 * new simple test type without objects from initial example code
 * @param b New required field requires a default value to avoid major breaking change by SemVer
 * @param c New optional field will default to 'None'
 */
final class Greetings private (
  val sub: String,
  val iss: String,
  val rel: String,
  val iat: Option[String],
  val reln: Option[String],
  val impId: Option[String],
  val a: String,
  val b: Option[Int],
  val c: Option[String]) extends Serializable {
  def customToString = s"original fields:  sub=${sub} iss=${iss} rel=${rel} iat=${iat} reln=${reln}"
  private def this(sub: String, iss: String, rel: String, iat: Option[String]) = this(sub, iss, rel, iat, Option("csr"), None, "self", None, None)
  private def this(sub: String, iss: String, rel: String, iat: Option[String], reln: Option[String], impId: Option[String]) = this(sub, iss, rel, iat, reln, impId, "self", None, None)
  
  override def equals(o: Any): Boolean = o match {
    case x: Greetings => (this.sub == x.sub) && (this.iss == x.iss) && (this.rel == x.rel) && (this.iat == x.iat) && (this.reln == x.reln) && (this.impId == x.impId) && (this.a == x.a) && (this.b == x.b) && (this.c == x.c)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (17 + "api.schema.Greetings".##) + sub.##) + iss.##) + rel.##) + iat.##) + reln.##) + impId.##) + a.##) + b.##) + c.##)
  }
  override def toString: String = {
    return s"sub=${sub} iss=${iss} rel=${rel} iat=${iat} reln=${reln} impId=${impId} a=${a} b=${b} c=${c}"
  }
  private[this] def copy(sub: String = sub, iss: String = iss, rel: String = rel, iat: Option[String] = iat, reln: Option[String] = reln, impId: Option[String] = impId, a: String = a, b: Option[Int] = b, c: Option[String] = c): Greetings = {
    new Greetings(sub, iss, rel, iat, reln, impId, a, b, c)
  }
  def withSub(sub: String): Greetings = {
    copy(sub = sub)
  }
  def withIss(iss: String): Greetings = {
    copy(iss = iss)
  }
  def withRel(rel: String): Greetings = {
    copy(rel = rel)
  }
  def withIat(iat: Option[String]): Greetings = {
    copy(iat = iat)
  }
  def withIat(iat: String): Greetings = {
    copy(iat = Option(iat))
  }
  def withReln(reln: Option[String]): Greetings = {
    copy(reln = reln)
  }
  def withReln(reln: String): Greetings = {
    copy(reln = Option(reln))
  }
  def withImpId(impId: Option[String]): Greetings = {
    copy(impId = impId)
  }
  def withImpId(impId: String): Greetings = {
    copy(impId = Option(impId))
  }
  def withA(a: String): Greetings = {
    copy(a = a)
  }
  def withB(b: Option[Int]): Greetings = {
    copy(b = b)
  }
  def withB(b: Int): Greetings = {
    copy(b = Option(b))
  }
  def withC(c: Option[String]): Greetings = {
    copy(c = c)
  }
  def withC(c: String): Greetings = {
    copy(c = Option(c))
  }
}
object Greetings {
  def sayHi = s"Hello from the companion object"
  def apply(sub: String, iss: String, rel: String, iat: Option[String]): Greetings = new Greetings(sub, iss, rel, iat)
  def apply(sub: String, iss: String, rel: String, iat: String): Greetings = new Greetings(sub, iss, rel, Option(iat))
  def apply(sub: String, iss: String, rel: String, iat: Option[String], reln: Option[String], impId: Option[String]): Greetings = new Greetings(sub, iss, rel, iat, reln, impId)
  def apply(sub: String, iss: String, rel: String, iat: String, reln: String, impId: String): Greetings = new Greetings(sub, iss, rel, Option(iat), Option(reln), Option(impId))
  def apply(sub: String, iss: String, rel: String, iat: Option[String], reln: Option[String], impId: Option[String], a: String, b: Option[Int], c: Option[String]): Greetings = new Greetings(sub, iss, rel, iat, reln, impId, a, b, c)
  def apply(sub: String, iss: String, rel: String, iat: String, reln: String, impId: String, a: String, b: Int, c: String): Greetings = new Greetings(sub, iss, rel, Option(iat), Option(reln), Option(impId), a, Option(b), Option(c))
}

```

 



