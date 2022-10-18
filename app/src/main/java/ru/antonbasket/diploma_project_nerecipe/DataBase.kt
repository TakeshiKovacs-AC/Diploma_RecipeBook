package ru.antonbasket.diploma_project_nerecipe

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.antonbasket.diploma_project_nerecipe.dao_room.DaoOfInstructions
import ru.antonbasket.diploma_project_nerecipe.dao_room.DaoOfRecipe
import ru.antonbasket.diploma_project_nerecipe.entity.InstructionsEntity
import ru.antonbasket.diploma_project_nerecipe.entity.RecipeEntity

@Database(entities = [RecipeEntity::class, InstructionsEntity::class], version = 1)
abstract class DataBase: RoomDatabase() {
    abstract fun recipeDao(): DaoOfRecipe
    abstract fun instructionsDao(): DaoOfInstructions

    companion object {
        @Volatile
        private var example: DataBase? = null

        fun getExample(context: Context): DataBase {
            return example ?: synchronized(this) {
                example ?: buildDatabase(context).also { example = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, DataBase::class.java, "app10.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}