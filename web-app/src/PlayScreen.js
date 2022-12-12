import React, { Component } from "react";
import Songs from "./Songs";
import AlbumInfo from "./AlbumInfo";
import Menu from "./Menu";

class PlayScreen extends React.Component {

    constructor(props) {
        super(props);
        this.state = { allSongs: [], songs: [], likes: [], currentSong: {}, history: [], preloadedPlaylists: null, authCompleted: false }
        if (localStorage.getItem("auth_data")) {
            var authData = JSON.parse(localStorage.getItem("auth_data"));
            this.state.id = authData.id;
            this.state.username = authData.username;
            this.state.token = authData.token;
            this.state.authCompleted = true;
        }
        if (localStorage.getItem("history")) {
            var history = JSON.parse(localStorage.getItem("history"));
            this.state.history = history ? history : [];
        }
        if (localStorage.getItem("all_songs")) {
            var allSongs = JSON.parse(localStorage.getItem("all_songs"));
            console.log(`Preloaded ${allSongs.length} songs from the storage`);
            this.state.allSongs = allSongs ? allSongs : [];
        }
        if (localStorage.getItem("likes")) {
            var likes = JSON.parse(localStorage.getItem("likes"));
            this.state.likes = likes ? likes : [];
        }
        this.likeSong = this.likeSong.bind(this);
        const http = new XMLHttpRequest()
        this.changeSong = this.changeSong.bind(this);
        this.processRegistration = this.processRegistration.bind(this);
        this.processAuth = this.processAuth.bind(this);
        this.acceptAuthToken = this.acceptAuthToken.bind(this);
        this.resetAuth = this.resetAuth.bind(this);
        this.changeSongs = this.changeSongs.bind(this);
        this.preloadUserPlaylists = this.preloadUserPlaylists.bind(this);
        if (this.state.allSongs && this.state.allSongs.length >= 10) {
            console.log("Preloading random songs from local storage");
            const shuffled = this.state.allSongs.sort(() => 0.5 - Math.random());
            let selected = shuffled.slice(0, 10);
            this.state.songs = selected;
        } else {
            this.getRandomSongs();
        }
        this.preloadSongs();
        if (this.state.id) {
            this.preloadUserPlaylists();
            this.preloadUserLikes(this.state.id);
        }
    }

    preloadUserPlaylists() {
            fetch('http://localhost:4000/playlist/', {
                method: 'GET',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(response => response.json())
              .then(response => { 
                console.log(`Preloaded playlists ${JSON.stringify(response)}`);
                var preloaded = response.filter(ex => ex.user_id == this.state.id);
                console.log(`Preloaded playlists ${JSON.stringify(preloaded)}`);
                this.setState({ preloadedPlaylists: preloaded });
            })
    }

    preloadUserLikes(user_id) {
        fetch('http://localhost:4001/user/' + user_id, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(response => response.json())
          .then(response => { 
            console.log(`Preloaded likes ${JSON.stringify(response.favorites.length)}`);
            this.setState({ likes: response.favorites });
        })
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

    likeSong(song_id) {
        let likes = this.state.likes;
        const index = likes.indexOf(song_id);
        if (index > -1) { // only splice array when item is found
            likes.splice(index, 1); // 2nd parameter means remove one item only
            this.sendDislikeToService(song_id, this.state.id);
        } else {
            likes.push(song_id);
            this.sendLikeToService(song_id, this.state.id);
        }
        localStorage.setItem("likes", JSON.stringify(likes));
        this.setState({likes: likes});
    }

    sendLikeToService(song_id, user_id) {
        if (!user_id) {
            user_id = "notset";
        }
            var dto = { song_id, user_id };
            console.log(JSON.stringify(dto));
            fetch('http://localhost:4001/song/favorite', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dto)
            }).then(response => console.log(response.status));
              
    }

    sendDislikeToService(song_id, user_id) {
        if (!user_id) {
            user_id = "notset";
        }
        var dto = { song_id, user_id };
        fetch('http://localhost:4001/song/favorite', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dto)
        }).then(response => console.log(response.status));
}

    resetAuth() {
        this.setState({ likes: [], id: "", token: "", username: "", preloadedPlaylists: [], authCompleted: false });
        localStorage.removeItem("auth_data");
        localStorage.removeItem("likes");
    }

    acceptAuthToken({ id, username, token }) {
        this.setState({ id, token, username, authCompleted: true })
        var auth_data = {id, token, username};
        localStorage.setItem("auth_data", JSON.stringify(auth_data));
        this.preloadUserPlaylists();
        this.preloadUserLikes(id);
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

    getRandomSongs() {
        fetch('http://localhost:44144/random/10')
            .then(response => response.json())
            .then(data => {
                this.processData(data);
            })
    }

    preloadSongs() {
        fetch('http://localhost:44144/track', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                this.preloadAllSongs(data);
            });
    }

    preloadAllSongs(data) {
        var songs = data.payload;       
        console.log(`Preloaded ${songs.length} songs`);
        localStorage.setItem("all_songs", JSON.stringify(songs));
        this.setState({ allSongs: songs})
    }

    processData(data) {
        var songs = data.payload;
        console.log(`LOADED ${songs.length} RANDOM SONGS`);
        this.setState({ songs: songs, currentSong: {} })
    }

    render() {
        return (
            <>
                <Menu preloadedPlaylists={this.state.preloadedPlaylists} changeSongs={this.changeSongs} resetAuth={this.resetAuth} userId={this.state.id} username={this.state.username} passAuthToken={this.acceptAuthToken} authCompleted={this.state.authCompleted} 
                processAuth={this.processAuth} processRegistration={this.processRegistration} history={this.state.history} changeSongCallBack={this.changeSong}
                />
                <AlbumInfo song={this.state.currentSong} />
                <Songs userId={this.state.id} likes={this.state.likes} likeSong={this.likeSong} currentSong={this.state.currentSong} songs={this.state.songs} changeSongCallBack={this.changeSong} />
            </>
        );
    }
}

export default PlayScreen;