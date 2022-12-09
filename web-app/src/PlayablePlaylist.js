import React, { Component } from "react";
import "./Menu.css"
class PlayablePlaylist extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <p className="menu-item" onClick={e => { this.props.changePlaylist(this.props.playlist); e.stopPropagation()}}>{this.props.playlist.playlist_name}</p>
        );
    }
}

export default PlayablePlaylist;