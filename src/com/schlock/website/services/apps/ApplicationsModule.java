package com.schlock.website.services.apps;

import com.schlock.website.services.apps.bingo.BingoRandomizer;
import com.schlock.website.services.apps.bingo.impl.FifthGradeVocabBingoRandomizerImpl;
import com.schlock.website.services.apps.bingo.impl.FifthGradeVocabBingoService;
import com.schlock.website.services.apps.bingo.impl.HighSchoolSelfIntroBingoRandomizerImpl;
import com.schlock.website.services.apps.bingo.impl.HighSchoolSelfIntroBingoService;
import com.schlock.website.services.apps.japanese.KanjiQuizRandomizer;
import com.schlock.website.services.apps.japanese.impl.KanjiQuizRandomizerImpl;
import com.schlock.website.services.apps.notfibbage.NotFibbageController;
import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import com.schlock.website.services.apps.notfibbage.impl.NotFibbageControllerImpl;
import com.schlock.website.services.apps.notfibbage.impl.NotFibbageManagementImpl;
import com.schlock.website.services.apps.pocket.PocketDataService;
import com.schlock.website.services.apps.pocket.PocketImageService;
import com.schlock.website.services.apps.pocket.impl.PocketDataServiceImpl;
import com.schlock.website.services.apps.pocket.impl.PocketImageServiceImpl;
import com.schlock.website.services.apps.pokemon.*;
import com.schlock.website.services.apps.pokemon.impl.*;
import com.schlock.website.services.apps.ps2.PlaystationImageService;
import com.schlock.website.services.apps.ps2.PlaystationPropertyService;
import com.schlock.website.services.apps.ps2.PlaystationService;
import com.schlock.website.services.apps.ps2.impl.PlaystationImageServiceImpl;
import com.schlock.website.services.apps.ps2.impl.PlaystationPropertyServiceImpl;
import com.schlock.website.services.apps.ps2.impl.PlaystationServiceImpl;
import com.schlock.website.services.apps.ranking.RankingService;
import com.schlock.website.services.apps.ranking.impl.RankingServiceImpl;
import com.schlock.website.services.apps.subtitles.SubtitleFixerService;
import com.schlock.website.services.apps.subtitles.impl.SubtitleFixerServiceImpl;
import org.apache.tapestry5.ioc.ServiceBinder;

public class ApplicationsModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(SubtitleFixerService.class, SubtitleFixerServiceImpl.class);

        binder.bind(BingoRandomizer.class, FifthGradeVocabBingoRandomizerImpl.class)
                .withId(FifthGradeVocabBingoRandomizerImpl.class.toString())
                .withMarker(FifthGradeVocabBingoService.class);

        binder.bind(BingoRandomizer.class, HighSchoolSelfIntroBingoRandomizerImpl.class)
                .withId(HighSchoolSelfIntroBingoRandomizerImpl.class.toString())
                .withMarker(HighSchoolSelfIntroBingoService.class);

        binder.bind(KanjiQuizRandomizer.class, KanjiQuizRandomizerImpl.class);

        binder.bind(NotFibbageManagement.class, NotFibbageManagementImpl.class);
        binder.bind(NotFibbageController.class, NotFibbageControllerImpl.class);

        binder.bind(RankingService.class, RankingServiceImpl.class);

        binder.bind(PocketDataService.class, PocketDataServiceImpl.class);
        binder.bind(PocketImageService.class, PocketImageServiceImpl.class);

        binder.bind(PokemonDataService.class, PokemonDataServiceImpl.class);
        binder.bind(PokemonDataGamepressService.class, PokemonDataGamepressServiceImpl.class);
        binder.bind(PokemonDataGameMasterService.class, PokemonDataGameMasterServiceImpl.class);

        binder.bind(PokemonRaidCounterService.class, PokemonRaidCounterServiceImpl.class);
        binder.bind(PokemonCounterCalculationService.class, PokemonCounterCalculationServiceImpl.class);

        binder.bind(PokemonCustomCounterPrimeService.class, PokemonCustomCounterPrimeServiceImpl.class);
        binder.bind(PokemonCustomCounterSecondService.class, PokemonCustomCounterSecondServiceImpl.class);

        binder.bind(PokemonRocketCounterService.class, PokemonRocketCounterServiceImpl.class);
        binder.bind(PokemonUnownService.class, PokemonUnownServiceImpl.class);

        binder.bind(PlaystationService.class, PlaystationServiceImpl.class);
        binder.bind(PlaystationImageService.class, PlaystationImageServiceImpl.class);
        binder.bind(PlaystationPropertyService.class, PlaystationPropertyServiceImpl.class);
    }
}