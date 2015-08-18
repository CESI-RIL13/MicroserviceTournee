class UrlMappings {

    static mappings = {

        "/alles"(resources:"alle") {
            "/ranges"(resources:"range")
        }
        "/ranges"(resources:"range") {
            "/travees"(resources:"travee")
            "/alle"(resource:"alle")
        }
        "/travees"(resources:"travee") {
            "/alveoles"(resources:"alveole")
            "/range"(resource:"range")
        }
        "/alveoles"(resources:"alveole") {
            "/emplacements"(resources:"emplacement")
            "/travee"(resource:"travee")
        }
        "/emplacements"(resources:"emplacement") {
            "/trajets"(resources:"trajet")
            "/alveole"(resource:"alveole")
        }
        "/trajets"(resources:"trajet") {
            "/depart"(resource:"emplacement")
            "/arrive"(resource:"emplacement")
            "/tournee"(resource:"tournee")
        }
        "/tournees"(resources:"tournee") {
            "/trajets"(resources:"trajet")
        }
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
