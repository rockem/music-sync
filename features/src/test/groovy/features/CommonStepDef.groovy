package features

import features.support.MusicSyncWorld


this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

World {
    new MusicSyncWorld()
}

Before {
    new File(TARGET_PATH).delete()
    new File(ITUNES_PATH).delete()
}

