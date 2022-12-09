import React, { Component } from "react";
import "./Menu.css"
class PlayablePlaylist extends React.Component {

    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
        this.handleRightClick = this.handleRightClick.bind(this);
    }

    handleClick(e) {
            this.props.changePlaylist(this.props.playlist);
            e.stopPropagation();
    }

    handleRightClick(e) {
            console.log("Right click!")
            this.props.removePlaylist(this.props.playlist);
            e.preventDefault();
    }

    render() {
        return (
            <p className="menu-item" onContextMenu={e => this.handleRightClick(e)} onClick={e => this.handleClick(e)}>{this.props.playlist.playlist_name}</p>
        );
    }
}

export default PlayablePlaylist;