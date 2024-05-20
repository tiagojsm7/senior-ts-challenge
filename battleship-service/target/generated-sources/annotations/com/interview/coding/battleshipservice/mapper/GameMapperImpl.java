package com.interview.coding.battleshipservice.mapper;

import com.interview.coding.battleshipservice.model.Game;
import com.interview.coding.battleshipservice.repository.entity.GameEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-20T15:51:29+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.18 (Eclipse Adoptium)"
)
public class GameMapperImpl implements GameMapper {

    @Override
    public GameEntity map(Game source) {
        if ( source == null ) {
            return null;
        }

        GameEntity gameEntity = new GameEntity();

        gameEntity.setId( source.getId() );
        gameEntity.setPlayerOneId( source.getPlayerOneId() );
        gameEntity.setPlayerTwoId( source.getPlayerTwoId() );
        gameEntity.setVsComputer( source.isVsComputer() );
        gameEntity.setPlayerTurn( source.getPlayerTurn() );
        gameEntity.setPlayerOneField( serializeField( source.getPlayerOneField() ) );
        gameEntity.setPlayerTwoField( serializeField( source.getPlayerTwoField() ) );
        gameEntity.setCreatedAt( source.getCreatedAt() );
        gameEntity.setStartedAt( source.getStartedAt() );
        gameEntity.setFinishedAt( source.getFinishedAt() );
        gameEntity.setWinner( source.getWinner() );

        return gameEntity;
    }

    @Override
    public Game map(GameEntity source) {
        if ( source == null ) {
            return null;
        }

        Game game = new Game();

        game.setId( source.getId() );
        game.setPlayerOneId( source.getPlayerOneId() );
        game.setPlayerTwoId( source.getPlayerTwoId() );
        game.setVsComputer( source.isVsComputer() );
        game.setPlayerTurn( source.getPlayerTurn() );
        game.setPlayerOneField( deserializeField( source.getPlayerOneField() ) );
        game.setPlayerTwoField( deserializeField( source.getPlayerTwoField() ) );
        game.setCreatedAt( source.getCreatedAt() );
        game.setStartedAt( source.getStartedAt() );
        game.setFinishedAt( source.getFinishedAt() );
        game.setWinner( source.getWinner() );

        return game;
    }
}
