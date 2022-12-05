import React, { Component } from "react";
import "./RecentlyPlayedItem.css"

class RecentlyPlayedItem extends React.Component {

    render() {
        return (
        <>
        <div className="history-item" onClick={() => {this.props.changeSongCallBack(this.props.song, true)}}>
        <p className="recently-played-song">{this.props.song.name}</p>
        <p className="recently-played-band">{"by " + this.props.song.band}</p>
        </div>
        </>
        )
        
    }
}

export default RecentlyPlayedItem;