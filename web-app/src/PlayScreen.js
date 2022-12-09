import React, { Component } from "react";
import Songs from "./Songs";
import AlbumInfo from "./AlbumInfo";
import Menu from "./Menu";

class PlayScreen extends React.Component {

    constructor(props) {
        super(props);
        this.state = { allSongs: [], songs: [], currentSong: {}, history: [], authCompleted: false }
        if (localStorage.getItem("auth_data")) {
            var authData = JSON.parse(localStorage.getItem("auth_data"));
            this.state.id = authData.id;
            this.state.username = authData.username;
            this.state.token = authData.token;
            this.state.authCompleted = true;
        }
        if (localStorage.getItem("history")) {
            var history = JSON.parse(localStorage.getItem("history"));
            console.log(JSON.parse(localStorage.getItem(history)));
            this.state.history = history ? history : [];
        }
        const http = new XMLHttpRequest()
        this.changeSong = this.changeSong.bind(this);
        this.processRegistration = this.processRegistration.bind(this);
        this.processAuth = this.processAuth.bind(this);
        this.acceptAuthToken = this.acceptAuthToken.bind(this);
        this.resetAuth = this.resetAuth.bind(this);
        this.changeSongs = this.changeSongs.bind(this);
        http.open("GET", "http://localhost:44144/random/10")
        http.send()
        this.getState();
    }

    changeSongs(playlist) {
        var newSongs = [];
        for (var song of this.state.allSongs) {
            if (playlist.songs.includes(song.id)) {
                newSongs.push(song);
            }
        }
        this.setState({ songs: newSongs, currentSong: {} })
    }

    processRegistration(dto) {
        return fetch('http://localhost:44144/register', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dto)
        })
    }

    processAuth(dto) {
        return fetch('http://localhost:44144/auth', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dto)
        })
    }

    resetAuth() {
        this.setState({ id: "", token: "", username: "", authCompleted: false });
        localStorage.removeItem("auth_data");
    }

    acceptAuthToken({ id, username, token }) {
        this.setState({ id, token, username, authCompleted: true })
        var auth_data = {id, token, username};
        localStorage.setItem("auth_data", JSON.stringify(auth_data));
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
            var newAudio = new Audio(mp3);
            newAudio.play();
            if (!noHistory) {
                if (history[0] != song) {
                    history.unshift(song);
                }
                if (history.length > 5) {
                    history.pop();
                }
                console.log("Setting " + JSON.stringify(history));
                localStorage.setItem("history", JSON.stringify(history));
            }
        }
        this.setState({ currentSong: song, audio: newAudio, history: history });
    }

    getState() {
        fetch('http://localhost:44144/random/10')
            .then(response => response.json())
            .then(data => {
                this.processData(data);
            });
    }

    processData(data) {
        var songs = data.payload;
        this.setState({ allSongs: songs, songs: songs, currentSong: {} })
    }

    render() {
        return (
            <>
                <Menu changeSongs={this.changeSongs} resetAuth={this.resetAuth} userId={this.state.id} username={this.state.username} passAuthToken={this.acceptAuthToken} authCompleted={this.state.authCompleted} 
                processAuth={this.processAuth} processRegistration={this.processRegistration} history={this.state.history} changeSongCallBack={this.changeSong}
                />
                <AlbumInfo song={this.state.currentSong} />
                <Songs userId={this.state.id} currentSong={this.state.currentSong} songs={this.state.songs} changeSongCallBack={this.changeSong} />
            </>
        );
    }
}

export default PlayScreen;