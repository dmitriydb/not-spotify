import React, { Component } from "react";
import "./SongItem.css"
import PlayButton from "./PlayButton";
import SongInfo from "./SongInfo";
import SongInfoButton from "./SongInfoButton";
import SongLength from "./SongLength";

class SongItem extends React.Component {

    convertLength(seconds) {
        var minutes = Math.trunc(seconds / 60);
        var left = String(seconds % 60);
        if (left.length == 1) left = '0' + left;
        var result = String(minutes) + ":" + left;
        return result;
    }

    render() {
        console.log("Song Item callback = " + this.props.changeSongCallBack);
        console.log(this.props.song)
        var title = ""
        var band = "";
        var length = 0;
        if (this.props.song) {
            title = this.props.song.name
            band = this.props.song.band
            length = this.convertLength(this.props.song.length)
        }

        return (
            <>
                <div className="song-item">   
                    <PlayButton song={this.props.song} changeSongCallBack={this.props.changeSongCallBack}/>
                    <SongInfo title={title} band={band}/>
                    <SongInfoButton/>
                    <SongLength length={length}/>
                </div>
            </>
        );
    }
}

export default SongItem;