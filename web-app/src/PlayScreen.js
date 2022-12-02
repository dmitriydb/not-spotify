import React, { Component } from "react";
import Songs from "./Songs";
import AlbumInfo from "./AlbumInfo";
import Menu from "./Menu";

class PlayScreen extends React.Component {

    constructor(props) {
        super(props);
        this.state = {songs : [], currentSong: {}}
        const http = new XMLHttpRequest()

        this.changeSong = this.changeSong.bind(this);

        http.open("GET", "http://localhost:44144/track")
        http.send()
        
        this.getState();

    }

    changeSong(song) {
        if (this.state.audio) {
            console.log("AUDIO" + JSON.stringify(this.state.audio));
            this.state.audio.pause();
        }
        if (song.mp3File) {
            var mp3 = song.mp3File;
            mp3 = mp3.substring(9);
            mp3 = mp3.replace("\\", "/");
            mp3 = "http://localhost:44144/content/" + mp3;
            console.log("Playing " + mp3);
            var newAudio = new Audio(mp3);
            newAudio.play();    
        }
        this.setState({currentSong: song, audio: newAudio});
    }

    getState() {
        fetch('http://localhost:44144/track')
            .then(response => response.json())
            .then(data => {
                this.processData(data);
            });
    }

    processData(data) {
        var songs = data.payload.filter(song => song.albumCover);
        this.setState({songs: songs, currentSong: songs[0]})
    }

    render() {
        return (
            <>
                <Menu />
                <AlbumInfo song={this.state.currentSong}/>
                <Songs songs={this.state.songs} changeSongCallBack={this.changeSong}/>
            </>
        );
    }
}

export default PlayScreen;