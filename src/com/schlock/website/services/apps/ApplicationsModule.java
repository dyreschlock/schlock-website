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
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterCalculationService;
import com.schlock.website.services.apps.pokemon.PokemonRaidCounterService;
import com.schlock.website.services.apps.pokemon.PokemonRocketCounterService;
import com.schlock.website.services.apps.pokemon.PokemonUnownService;
import com.schlock.website.services.apps.pokemon.impl.PokemonRaidCounterCalculationServiceImpl;
import com.schlock.website.services.apps.pokemon.impl.PokemonRaidCounterServiceImpl;
import com.schlock.website.services.apps.pokemon.impl.PokemonRocketCounterServiceImpl;
import com.schlock.website.services.apps.pokemon.impl.PokemonUnownServiceImpl;
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

        binder.bind(PokemonRaidCounterService.class, PokemonRaidCounterServiceImpl.class);
        binder.bind(PokemonRaidCounterCalculationService.class, PokemonRaidCounterCalculationServiceImpl.class);
        binder.bind(PokemonRocketCounterService.class, PokemonRocketCounterServiceImpl.class);
        binder.bind(PokemonUnownService.class, PokemonUnownServiceImpl.class);
    }
}