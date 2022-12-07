import React, { Component } from "react";
import Songs from "./Songs";
import AlbumInfo from "./AlbumInfo";
import Menu from "./Menu";

class PlayScreen extends React.Component {

    constructor(props) {
        super(props);
        this.state = {songs : [], currentSong: {}, history: []}
        const http = new XMLHttpRequest()
        this.changeSong = this.changeSong.bind(this);
        http.open("GET", "http://localhost:44144/random/10")
        http.send()
        this.getState();
    }

    changeSong(song, noHistory = false) {
        if (this.state.audio) {
            this.state.audio.pause();
        }
        var history = this.state.history;
        if (song.mp3File) {
            var mp3 = song.mp3File;
            mp3 = mp3.substring(9);
            mp3 = mp3.replace("\\", "/");
            mp3 = "http://localhost:44144/content/" + mp3;
            console.log("Playing " + mp3);
            var newAudio = new Audio(mp3);
            newAudio.play(); 
            if (!noHistory) {
                if (history[0] != song) {
                    history.unshift(song);   
                }
                if (history.length > 5) {
                    history.pop();
                }
            }
        }
        this.setState({currentSong: song, audio: newAudio, history: history});
    }

    getState() {
        fetch('http://localhost:44144/random/10')
            .then(response => response.json())
            .then(data => {
                this.processData(data);
            });
    }

    processData(data) {
        var songs = data.payload.filter(song => song.albumCover);
        this.setState({songs: songs, currentSong: {}})
    }

    render() {
        return (
            <>
                <Menu history={this.state.history} changeSongCallBack={this.changeSong}/>
                <AlbumInfo song={this.state.currentSong}/>
                <Songs currentSong={this.state.currentSong} songs={this.state.songs} changeSongCallBack={this.changeSong}/>
            </>
        );
    }
}

export default PlayScreen;