import React, { Component } from "react";
import "./Playlist.css"
class Playlist extends React.Component {

    constructor(props) {
        super(props);
        this.handleRightClick = this.handleRightClick.bind(this);
    }

    handleRightClick(e) {
        console.log("Right click!")
        this.props.removePlaylist(this.props.playlist);
        e.preventDefault();
}

    render() {
        return (
            <label className={this.props.className} align="center" onContextMenu={e => this.handleRightClick(e)} onClick={e => { this.props.addSongToPlaylist(this.props.playlist.id); e.stopPropagation()}}>{this.props.playlist.playlist_name}</label>
        );
    }
}

export default Playlist;