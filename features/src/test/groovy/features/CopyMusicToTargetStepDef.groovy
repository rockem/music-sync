package features

import features.support.Playlist
import features.support.Song
import features.support.iTunesLibraryXmlBuilder
import features.support.iTunesXmlFileCreator
import org.rockem.ms.MusicSync

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

def PLAYLIST = "kuku"

Given(~/^I have a playlist defined$/) { ->
    xml = new iTunesLibraryXmlBuilder()
            .addSongs(
            new Song([id: 324, location: "file://$MUSIC_PATH/$MP3_FILE1"]),
            new Song([id: 456, location: "file://$MUSIC_PATH/$MP3_FILE2"]))
            .addPlaylists(new Playlist([id: 34, name: PLAYLIST, songs: [324, 456]]))
            .build()
    new iTunesXmlFileCreator(LIBRARY_XML_PATH).createFrom(xml)
}

When(~/^I execute$/) { ->
    new MusicSync().main((String[])[ITUNES_PATH, TARGET_PATH, PLAYLIST])
}

Then(~/^the files from the playlist should be copies to target$/) { ->
    assert new File("${TARGET_PATH}/$MP3_FILE1").exists()
    assert new File("${TARGET_PATH}/$MP3_FILE2").exists()
}
