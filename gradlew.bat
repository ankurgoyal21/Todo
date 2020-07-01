<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ui.home.ui.home.HomeFragment">

    <EditText
        android:id="@+id/etTxtSearch"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@drawable/ic_search"
        android:drawableStart="@android:drawable/ic_menu_search"
        android:drawablePadding="12dp"
        android:gravity="left|center"
        android:hint="@string/search"
        android:maxLength="1"
        android:padding="16dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/etTxtSearch"
        android:layout_margin="10dp">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">

            <Button
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/prompt_name"
                android:background="@drawable/oval"
                android:textColor="@android:color/white"></Button>
        </LinearLayout>

    </ScrollView>
    <!--<TextView
        android:id="@+id/text_home"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        android:textAlignment="center"
        android:textSize="20sp"
        app:layout_constraintBottom_to