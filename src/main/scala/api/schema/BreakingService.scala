package api.schema

class BreakingService {


  case class RsClaimSet(sub: String, sid: String, iat: String, breakingChange: String)


}
