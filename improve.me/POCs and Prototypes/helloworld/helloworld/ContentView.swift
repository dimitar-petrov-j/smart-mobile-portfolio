//
//  ContentView.swift
//  helloworld
//
//  Created by user196377 on 3/23/21.
//

import SwiftUI

struct ContentView: View {
    @State var tapCount = 0
    @State private var name = ""
    
    let students = ["Harry", "Hermione", "Ron"]
        @State private var selectedStudent = 0
    
    var body: some View {
        
        Form {
                Section {
                    Text("Hello World")
                }
            }.navigationBarTitle("SwiftUI", displayMode: .inline)
        
        Form {
                    TextField("Enter your name", text: $name)
            Text("Your name is \(name)")                }
        
        VStack {
                    Picker("Select your student", selection: $selectedStudent) {
                        ForEach(0 ..< students.count) {
                            Text(self.students[$0])
                        }
                    }
                    Text("You chose:  \(students[selectedStudent])")
                }
        
        Button("Tap Count: \(tapCount)") {
                    self.tapCount += 1
                }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
