import React, { Component } from "react";
import "./Songs.css"
import SongItem
 from "./SongItem";
class Songs extends React.Component {
    
    render() {
        
        var content = []
        for (var i = 0; i < this.props.songs.length; i++) {
            var song = this.props.songs[i];
            content.push(<SongItem likes={this.props.likes} likeSong={this.props.likeSong} userId={this.props.userId} currentSong={this.props.currentSong} key={i} song={song} changeSongCallBack={this.props.changeSongCallBack}/>)
        }


        return (
            <>
                <div className="songs">
                    {content}
                </div>
            </>
        );
    }
}

export default Songs;