//
//  SimpleloginIOSApp.swift
//  IosApp
//
//  Created by Ramesh P on 31/05/25.
//

import SwiftUI
import Shared

@main
struct SimpleloginIOSApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView(viewModel: .init(loginRepository: LoginRepository(dataSource: LoginDataSource()), loginValidator: LoginDataValidator()))
        }
    }
}
