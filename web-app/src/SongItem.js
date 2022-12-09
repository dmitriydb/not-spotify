import React, { Component } from "react";
import "./SongItem.css"
import PlayButton from "./PlayButton";
import SongInfo from "./SongInfo";
import SongInfoButton from "./SongInfoButton";
import SongLength from "./SongLength";
import AddToPlaylistButton from "./AddToPlaylistButton";
import LikeButton from "./LikeButton";

class SongItem extends React.Component {

    convertLength(seconds) {
        var minutes = Math.trunc(seconds / 60);
        var left = String(seconds % 60);
        if (left.length == 1) left = '0' + left;
        var result = String(minutes) + ":" + left;
        return result;
    }

    render() {
        var title = ""
        var band = "";
        var length = 0;
        if (this.props.song) {
            title = this.props.song.name
            band = this.props.song.band
            length = this.convertLength(this.props.song.length)
        }

        var isActive = (this.props.currentSong == this.props.song);
        return (
            <>
                <div className="song-item" onClick={() => {this.props.changeSongCallBack(this.props.song)}}>   
                    <PlayButton currentSong={this.props.currentSong} song={this.props.song} />
                    <SongInfo active={isActive} title={title} band={band}/>
                    <LikeButton userId={this.props.userId}/>
                    <AddToPlaylistButton userId={this.props.userId} song={this.props.song}/>
                    <SongInfoButton/>
                    <SongLength length={length}/>
                </div>
            </>
        );
    }
}

export default SongItem;