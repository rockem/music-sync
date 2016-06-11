package features.support

import groovy.xml.MarkupBuilder
import groovy.xml.MarkupBuilderHelper

class iTunesLibraryXmlBuilder {

    private static final String DOCTYPE = "<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n"

    private List<Song> songs = []
    private List<Playlist> playlists = []

    def addSongs(Song... songs) {
        this.songs.addAll(songs)
        return createBuilder();
    }

    def createBuilder() {
        return new iTunesLibraryXmlBuilder([songs: songs, playlists: playlists])
    }

    def addPlaylists(Playlist... playlists) {
        this.playlists.addAll(playlists)
        return createBuilder()
    }

    def build() {
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        addXmlDeclarationsTo(xml)
        xml.plist(version: "1.0") {
            dict {
                createVersionData(xml)
                key { mkp.yield 'Tracks' }
                dict {
                    songs.each { song ->
                        createSong(xml, song)
                    }
                }
                key { mkp.yield 'Playlists' }
                array {
                    playlists.each { playlist->
                        createPlaylist(xml, playlist)
                    }
                }


            }
        }
        return writer.toString()
    }

    private void addXmlDeclarationsTo(MarkupBuilder xml) {
        def helper = new MarkupBuilderHelper(xml)
        helper.xmlDeclaration([version: '1.0', encoding: 'UTF-8'])
        helper.yieldUnescaped(DOCTYPE)
    }

    def createVersionData(builder) {
        builder.key { mkp.yield 'Major Version' }
        builder.integer { mkp.yield '1' }
        builder.key { mkp.yield 'Minor Version' }
        builder.integer { mkp.yield '1' }
    }

    def createSong(builder, song) {
        builder.key { mkp.yield "${song.id}" }
        builder.dict {
            key { mkp.yield "Track ID" }
            integer { mkp.yield "${song.id}" }
            key { mkp.yield "Location"}
            string { mkp.yield "${song.location}" }
        }
    }

    def createPlaylist(builder, playlist) {
        builder.dict {
            key { mkp.yield "Playlist ID" }
            integer { mkp.yield "${playlist.id}" }
            key { mkp.yield "Name" }
            string { mkp.yield "${playlist.name}" }
            createPlaylistTracks(builder, playlist.songs)
        }
    }

    def createPlaylistTracks(builder, tracks) {
        builder.key { mkp.yield "Playlist Items" }
        builder.array {
            tracks.each { track ->
                dict {
                    key { mkp.yield "Track ID"}
                    integer { mkp.yield "${track}"}
                }
            }
        }
    }
}
