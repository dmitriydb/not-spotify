import React, { Component } from "react";
import "./Playlist.css"
class Playlist extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <label className={this.props.className} align="center" onClick={e => { this.props.addSongToPlaylist(this.props.playlist.id); e.stopPropagation()}}>{this.props.playlist.playlist_name}</label>
        );
    }
}

export default Playlist;