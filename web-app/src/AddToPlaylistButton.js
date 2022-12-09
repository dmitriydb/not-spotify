import React, { Component } from "react";
import "./AddToPlaylistButton.css"
import Modal from 'react-modal';
import Playlist from "./Playlist";

class AddToPlaylistButton extends React.Component {

    constructor(props) {
        super(props);
        this.state = { showModal: false, offsetX: 0, offsetY: 0, status: "", playlists: [] };
        this.closeModal = this.closeModal.bind(this);
        this.showModal = this.showModal.bind(this);
        this.createPlaylist = this.createPlaylist.bind(this);
        this.playlistName = React.createRef();
        this.getPlaylists = this.getPlaylists.bind(this);
        this.addSongToPlaylist = this.addSongToPlaylist.bind(this);
        this.removeSongFromPlaylist = this.removeSongFromPlaylist.bind(this);
    }

    removeSongFromPlaylist(playlist_id) {
        var song_id = this.props.song.id;
        var dto = { song_id, playlist_id };
        fetch('http://localhost:4000/playlist/song', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dto)
        }).then(response => response.json())
          .then(response => {             
            this.closeModal();
        })
    }

    closeModal() {
        this.setState({ showModal: false });
    }

    showModal(x, y) {
        this.getPlaylists();
        this.setState({ showModal: true, offsetX: x, offsetY: y });
    }

    createPlaylist() {
        var playlistName = this.playlistName.current.value;
        if (!playlistName) {
            this.setState({ status: "Playlist name is empty" });
        } else {
            var user_id = this.props.userId ? this.props.userId : "not set";
            var dto = { user_id: user_id, playlist_name: playlistName };
            fetch('http://localhost:4000/playlist', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dto)
            }).then(response => response.json())
                .then(response => {
                    var createdPlaylistId = response.id;
                    this.addSongToPlaylist(createdPlaylistId);
                })
        }
    }

    getPlaylists() {
        fetch('http://localhost:4000/playlist/', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(response => response.json())
            .then(response => { this.setState({ playlists: response.filter(ex => ex.user_id == this.props.userId) }) })
    }

    addSongToPlaylist(playlist_id) {
        var song_id = this.props.song.id;
        var dto = { song_id, playlist_id };
        fetch('http://localhost:4000/playlist/song', {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dto)
        }).then(response => response.json())
          .then(response => {             
            this.closeModal();
        })
    }

    render() {
        var style = {
            backgroundColor: "black",
            left: this.state.offsetX,
            position: "absolute",
            top: this.state.offsetY,
            width: "150px",
        }
        var userPlaylists = [];
        for (var playlist of this.state.playlists) {
            var songClassname = "playlist-label";
            if (playlist.songs.includes(this.props.song.id)) {
                songClassname = "playlist-label-active";
            } else {
            }
            userPlaylists.push(
                <Playlist playlist={playlist} removeSongFromPlaylist={this.removeSongFromPlaylist} addSongToPlaylist={this.addSongToPlaylist} className={songClassname}></Playlist>
            )
        }
        return (
            <>
                <div className="add-to-playlist-button">
                    <img src="img/add-to-playlist.png" onClick={(e) => {
                        const posX = e.clientX;
                        const posY = e.clientY;
                        this.showModal(posX, posY);
                        e.stopPropagation();
                    }}></img>
                    <Modal
                        isOpen={this.state.showModal}
                        className="modal-content-playlist"
                    >
                        <div className="buttons" style={style}>
                            {userPlaylists}
                            <label className="playlist-label" align="center">{this.state.status}</label>
                            <input onClick={(e) => { e.stopPropagation() }} autoComplete="chrome-off" className="playlist-input" type="text" name="playlist-name" required ref={this.playlistName} />
                            <button type="button" className="signupbtn" onClick={(e) => { this.createPlaylist(); e.stopPropagation(); }}>New</button>
                            <button type="button" className="cancelbtn" onClick={(e) => { this.closeModal(); e.stopPropagation(); }}>Cancel</button>
                        </div>
                    </Modal>
                </div>
            </>
        );
    }
}

export default AddToPlaylistButton;