//
//  ContentView.swift
//  IosApp
//
//  Created by Ramesh P on 31/05/25.
//

import SwiftUI
import Shared

struct ContentView: View {
    var body: some View {
        Text(Greeting().greet())
        .padding()
    }
}

#Preview {
    ContentView()
}
